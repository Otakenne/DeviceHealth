package com.otakenne.devicehealthsdk.data.datasources

import com.otakenne.devicehealthsdk.data.utility.Result
import java.io.File
import java.io.FileFilter
import java.io.IOException
import java.io.RandomAccessFile
import java.lang.Math.ceil
import java.util.regex.Pattern

/***
 * Get system CPU load datasource implementation (for devices not running Android O+)
 */
internal class SystemCPULoadDataSource: ISystemCPULoadDataSource {
    override fun getSystemCPULoadDataSource(): Result<Int> {

        //get how many cores there are from function
        //get how many cores there are from function
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
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter: FileFilter {
            override fun accept(pathname: File): Boolean {
                //Check if filename is "cpu", followed by one or more digits
                return Pattern.matches("cpu[0-9]+", pathname.name)
            }
        }
        return try {
            //Get directory containing CPU info
            val dir = File("/sys/devices/system/cpu/")
            //Filter to only list the devices we care about
            val files: Array<File> = dir.listFiles(CpuFilter()) as Array<File>
            //Return the number of cores (virtual CPU devices)
            files.size
        } catch (e: Exception) {
            //Default to return 1 core
            1
        }
    }

    /***
     * Get's CPU core information
     */
    private fun readCore(i: Int): Float {
        /*
             * how to calculate multicore
             * this function reads the bytes from a logging file in the android system (/proc/stat for cpu values)
             * then puts the line into a string
             * then spilts up each individual part into an array
             * then(since he know which part represents what) we are able to determine each cpu total and work
             * then combine it together to get a single float for overall cpu usage
             */
        try {
            val reader = RandomAccessFile("/proc/stat", "r")
            //skip to the line we need
            for (ii in 0 until i + 1) {
                reader.readLine()
            }
            var load: String = reader.readLine()

            //cores will eventually go offline, and if it does, then it is at 0% because it is not being
            //used. so we need to do check if the line we got contains cpu, if not, then this core = 0
            return if (load.contains("cpu")) {
                var toks = load.split(" ").toTypedArray()

                //we are recording the work being used by the user and system(work) and the total info
                //of cpu stuff (total)
                //https://stackoverflow.com/questions/3017162/how-to-get-total-cpu-usage-in-linux-c/3017438#3017438
                val work1 = toks[1].toLong() + toks[2].toLong() + toks[3].toLong()
                val total1 =
                    toks[1].toLong() + toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[5].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
                try {
                    //short sleep time = less accurate. But android devices typically don't have more than
                    //4 cores, and I'n my app, I run this all in a second. So, I need it a bit shorter
                    Thread.sleep(500)
                } catch (e: java.lang.Exception) {
                }
                reader.seek(0)
                //skip to the line we need
                for (ii in 0 until i + 1) {
                    reader.readLine()
                }
                load = reader.readLine()
                //cores will eventually go offline, and if it does, then it is at 0% because it is not being
                //used. so we need to do check if the line we got contains cpu, if not, then this core = 0%
                if (load.contains("cpu")) {
                    reader.close()
                    toks = load.split(" ").toTypedArray()
                    val work2 = toks[1].toLong() + toks[2].toLong() + toks[3].toLong()
                    val total2 =
                        toks[1].toLong() + toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[5].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()


                    //here we find the change in user work and total info, and divide by one another to get our total
                    //seems to be accurate need to test on quad core
                    //https://stackoverflow.com/questions/3017162/how-to-get-total-cpu-usage-in-linux-c/3017438#3017438
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