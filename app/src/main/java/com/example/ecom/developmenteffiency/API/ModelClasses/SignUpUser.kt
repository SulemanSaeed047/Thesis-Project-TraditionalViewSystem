package com.example.ecom.developmenteffiency.API.ModelClasses

data class SignUpUser(
    val email: String ,
    val username: String ,
    val password: String,
    val name: Name = Name("", ""),
    val address: Address = Address(
        city = "Default City",
        street = "Default Street",
        number = 0,
        zipcode = "00000",
        geolocation = Geolocation("0.0", "0.0")
    ),
    val id:Long = 0L,
    val phone: String = "000-000-0000"
)

data class Name(
    val firstname: String = "Default",
    val lastname: String = "User"
)

data class Address(
    val city: String = "Default City",
    val street: String = "Default Street",
    val number: Int = 0,
    val zipcode: String = "00000",
    val geolocation: Geolocation = Geolocation("0.0", "0.0")
)

data class Geolocation(
    val lat: String = "0.0",
    val long: String = "0.0"
)
