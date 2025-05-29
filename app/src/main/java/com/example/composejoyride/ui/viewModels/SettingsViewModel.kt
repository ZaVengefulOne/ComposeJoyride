package com.example.composejoyride.ui.viewModels

import android.content.Context
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import com.example.composejoyride.ui.theme.TheFont
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(@ApplicationContext context: Context): ViewModel() {

    //private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    fun updateAdminStatus(value: Boolean) {
        _isAdmin.value = value
    }

    fun setFont(font: FontFamily) {
        TheFont = font
    }

    fun fetchAdminStatus() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Firebase.firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                _isAdmin.value = doc.getBoolean("isAdmin") ?: false
            }
    }
}