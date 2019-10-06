package com.example.uzair.blazeimageloader.util

class NetworkState(val status: Status, val msg: String) {
    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    public companion object {
        public val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        public val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
    }
}