package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result
import com.otakenne.devicehealthsdk.data.utility.doNothing
import java.io.File
import java.io.FileFilter
import java.io.IOException
import java.io.RandomAccessFile
import java.util.regex.Pattern

/***
 * Get system CPU load datasource implementation (for devices not running Android O+)
 */
internal class SystemCPULoadDataSource: ISystemCPULoadDataSource {
    override fun getSystemCPULoadDataSource(): Result<Int> {

        val numCores = getNumCores()
        val coreValues = FloatArray(numCores)
        for (i in 0 until numCores) {
            coreValues[i] = readCore(i) * 100
        }
        val averageUsageAcrossCores = kotlin.math.ceil(coreValues.average())
        return Result.Success(averageUsageAcrossCores.toInt())
    }

    /***
     * Get the number of cores
     */
    private fun getNumCores(): Int {
        class CpuFilter: FileFilter {
            override fun accept(pathname: File): Boolean {
                return Pattern.matches("cpu[0-9]+", pathname.name)
            }
        }
        return try {
            val dir = File("/sys/devices/system/cpu/")
            val files: Array<File> = dir.listFiles(CpuFilter()) as Array<File>
            files.size
        } catch (e: Exception) {
            1
        }
    }

    /***
     * Get's CPU core information
     */
    private fun readCore(i: Int): Float {
        try {
            val reader = RandomAccessFile("/proc/stat", "r")
            for (ii in 0 until i + 1) {
                reader.readLine()
            }
            var load: String = reader.readLine()

            return if (load.contains("cpu")) {
                var toks = load.split(" ").toTypedArray()

                val work1 = toks[1].toLong() + toks[2].toLong() + toks[3].toLong()
                val total1 =
                    toks[1].toLong() + toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[5].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
                try {
                    Thread.sleep(500)
                } catch (e: java.lang.Exception) {
                    doNothing()
                }

                reader.seek(0)
                for (ii in 0 until i + 1) {
                    reader.readLine()
                }
                load = reader.readLine()
                if (load.contains("cpu")) {
                    reader.close()
                    toks = load.split(" ").toTypedArray()
                    val work2 = toks[1].toLong() + toks[2].toLong() + toks[3].toLong()
                    val total2 =
                        toks[1].toLong() + toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[5].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
                    (work2 - work1).toFloat() / (total2 - total1)
                } else {
                    reader.close()
                    0f
                }
            } else {
                reader.close()
                0f
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return 0f
    }
}