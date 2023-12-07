package com.prathamngundikere.lazymoney.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prathamngundikere.lazymoney.R
import com.prathamngundikere.lazymoney.ux.data.TransactionEntity
import com.prathamngundikere.lazymoney.ux.presentation.TransactionEvent
import com.prathamngundikere.lazymoney.ux.presentation.TransactionState
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    state: TransactionState,
    navController: NavController,
    onEvent: (TransactionEvent) -> Unit
) {
    var currentBalance by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }
    var cardBalance by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }
    var cashBalance by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }
    Scaffold(
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
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    state.type.value = "Income"
                    state.amount.value = 0.0
                    state.name.value = ""
                    state.paymentMethod.value = "Cash"
                    navController.navigate("InputScreen")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {paddingValues ->
        val currentBalance = calculateBalance(state.transactions, "Income") -
                calculateBalance(state.transactions, "Expenditure")
        val cardBalance = calculateBalance(state.transactions, "Income", "Card") -
                calculateBalance(state.transactions, "Expenditure", "Card")
        val cashBalance = calculateBalance(state.transactions, "Income", "Cash") -
                calculateBalance(state.transactions, "Expenditure", "Cash")

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item{
                CurrentBalance(cb = currentBalance)
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    BalanceCard(
                        icon = Icons.Filled.AccountBalance,
                        balance = cardBalance,
                        typeName = "Card"
                    )
                    BalanceCard(
                        icon = Icons.Filled.Payments,
                        balance = cashBalance,
                        typeName = "Cash"
                    )
                }
            }
            item { TransactionList() }
            items(state.transactions.size) {index ->
                TransactionItem(
                    state = state,
                    index = index,
                    onEvent = onEvent
                )
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "End of the list",
                        color = MaterialTheme.colorScheme.primary)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp))
                }
            }
        }
    }
}
fun calculateBalance(transactions: List<TransactionEntity>, type: String, paymentMethod: String? = null): Double {
    return transactions
        .filter { it.type == type && (paymentMethod.isNullOrBlank() || it.paymentMethod == paymentMethod) }
        .sumByDouble { if (type == "Income") it.amount else it.amount }
}


@Composable
fun CurrentBalance(
    modifier: Modifier = Modifier,
    cb: Double
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Balance",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "₹${cb.toString()}0",
                fontSize = 55.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun BalanceCard(
    icon: ImageVector,
    balance: Double,
    typeName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = icon ,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "₹${balance.toString()}0",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = typeName,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = "Transactions",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.NavigateNext,
            contentDescription = "",
            Modifier.size(30.dp)
        )
    }
}

@Composable
fun TransactionItem(
    state: TransactionState,
    index: Int,
    onEvent: (TransactionEvent) -> Unit
) {
    // Convert milliseconds to LocalDateTime
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(state.transactions[index].dateTime),
        ZoneId.systemDefault()
    )

    // Format LocalDateTime to display date, month, and year
    val formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy-HH:mm:ss"))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.transactions[index].name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${state.transactions[index].paymentMethod}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${formattedDateTime}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            text = "₹${state.transactions[index].amount.toString()}0",
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (state.transactions[index].type == "Income") Color.Green else Color.Red
        )
    }
}

@Preview
@Composable
fun preview() {
    TransactionList()
}