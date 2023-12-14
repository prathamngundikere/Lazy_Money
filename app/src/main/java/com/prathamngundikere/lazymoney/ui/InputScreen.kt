package com.prathamngundikere.lazymoney.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prathamngundikere.lazymoney.R
import com.prathamngundikere.lazymoney.ux.presentation.TransactionEvent
import com.prathamngundikere.lazymoney.ux.presentation.TransactionState
//stable
enum class TransactionType {
    INCOME, EXPENDITURE
}

enum class PaymentMethod {
    CASH, CARD
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    state: TransactionState,
    navController: NavController,
    onEvent: (TransactionEvent) -> Unit
) {
    var transactionType by remember { mutableStateOf(TransactionType.INCOME) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }
    var transactionDate by remember { mutableStateOf("")}

    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold (
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(R.string.new_transaction),
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButton = {
            // Floating Action Button
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    if (state.name.value.isNotEmpty() && state.amount.value != 0.0) {
                        // Handle the values here
                        onEvent(
                            TransactionEvent.SaveTransaction(
                                type = state.type.value,
                                name = state.name.value,
                                amount = state.amount.value,
                                paymentMethod = state.paymentMethod.value
                            )
                        )
                        navController.popBackStack()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Save Transaction",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            item {
                // Choose Income or Expenditure
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Transaction Type")
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = transactionType == TransactionType.INCOME,
                                onClick = {
                                    transactionType = TransactionType.INCOME
                                    state.type.value = "Income"
                                },
                                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                            )
                            Text("Income")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = transactionType == TransactionType.EXPENDITURE,
                                onClick = {
                                    transactionType = TransactionType.EXPENDITURE
                                    state.type.value = "Expenditure"
                                },
                                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                            )
                            Text("Expenditure")
                        }
                    }
                }
            }
            item {
                // Choose Cash or Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Payment Method")
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = paymentMethod == PaymentMethod.CASH,
                                onClick = {
                                    paymentMethod = PaymentMethod.CASH
                                    state.paymentMethod.value = "Cash"
                                },
                                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                            )
                            Text("Cash")
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = paymentMethod == PaymentMethod.CARD,
                                onClick = {
                                    paymentMethod = PaymentMethod.CARD
                                    state.paymentMethod.value = "Card"
                                },
                                colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                            )
                            Text("Card")
                        }
                    }
                }
            }
            item {
                // Transaction Title Input
                OutlinedTextField(
                    value = transactionTitle,
                    onValueChange = { it ->
                        transactionTitle = it
                        state.name.value = it
                    },
                    label = { Text("Transaction Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )

                // Transaction Amount Input
                OutlinedTextField(
                    value = transactionAmount,
                    onValueChange = {
                        transactionAmount = it
                        state.amount.value = if (it.isNotEmpty()) {
                            it.toDouble()
                        } else {
                            0.0 // or any default value you prefer
                        }
                    },
                    label = { Text("Transaction Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}


