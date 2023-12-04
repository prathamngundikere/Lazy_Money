package com.prathamngundikere.lazymoney.ux

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.prathamngundikere.lazymoney.R

enum class TransactionType {
    INCOME, EXPENDITURE
}

enum class PaymentMethod {
    CASH, CARD
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputScreen() {
    var transactionType by remember { mutableStateOf(TransactionType.INCOME) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    var transactionTitle by remember { mutableStateOf("") }
    var transactionAmount by remember { mutableStateOf("") }
    var transactionDate by remember { mutableStateOf("")}


    val context = LocalContext.current
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
                    // Handle the values here
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
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
                            onClick = { transactionType = TransactionType.INCOME },
                            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Text("Income")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = transactionType == TransactionType.EXPENDITURE,
                            onClick = { transactionType = TransactionType.EXPENDITURE },
                            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Text("Expenditure")
                    }
                }
            }

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
                            onClick = { paymentMethod = PaymentMethod.CASH },
                            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Text("Cash")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = paymentMethod == PaymentMethod.CARD,
                            onClick = { paymentMethod = PaymentMethod.CARD },
                            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Text("Card")
                    }
                }
            }
            // Transaction Title Input
            OutlinedTextField(
                value = transactionTitle,
                onValueChange = { transactionTitle = it },
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
                onValueChange = { transactionAmount = it },
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

            // Transaction Date Input
            OutlinedTextField(
                value = transactionDate,
                onValueChange = { transactionDate = it },
                label = { Text("Transaction Date") },
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
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    InputScreen()
}

