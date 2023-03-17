package vn.vunganyen.algorithm

import java.util.*

fun miniMaxSum(arr : IntArray){
    var max = arr[0]
    var min= arr[0]
    var sum = arr[0]
    for(i in 1..arr.size-1){
        sum+=arr[i]
        if(max < arr[i]) max = arr[i]
        if(min > arr[i]) min = arr[i]
    }
    println("Output:")
    println((sum - max).toString() + " "+ (sum - min).toString())
}

fun main(){
    val reader = Scanner(System.`in`)
    var arr = IntArray(5)
    println("Input: ")
    for(i in 0..arr.size-1){
        arr[i] = reader.nextInt()
    }
    miniMaxSum(arr)
}