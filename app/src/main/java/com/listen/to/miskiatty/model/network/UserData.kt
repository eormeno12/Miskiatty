package com.listen.to.miskiatty.model.network

import java.io.Serializable

data class UserData(val productsJSON: String = "",
                    val ordersJSON: String = "",
                    val clientsJSON: String = "") : Serializable
