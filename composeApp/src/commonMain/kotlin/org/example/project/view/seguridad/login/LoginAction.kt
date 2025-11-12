package org.example.project.view.seguridad.login

import cafe.adriel.voyager.navigator.Navigator

sealed interface LoginAction {
    data class OnLoginFormSubmit(
        val usuario: String,
        val clave: String
    ) : LoginAction

    data class onUserValueChange(val usuario: String) : LoginAction
    data class onPasswordValueChange(val clave: String) : LoginAction
}