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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prathamngundikere.lazymoney.R
import com.prathamngundikere.lazymoney.ux.presentation.TransactionEvent
import com.prathamngundikere.lazymoney.ux.presentation.TransactionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(
    state: TransactionState,
    navController: NavController,
    onEvent: (TransactionEvent) -> Unit
) {
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
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item{
                CurrentBalance(cb = 25000.0)
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BalanceCard(
                        icon = Icons.Filled.AccountBalance,
                        balance = 25000.0,
                        typeName = "Bank Balance"
                    )
                    BalanceCard(
                        icon = Icons.Filled.Payments,
                        balance = 678.0,
                        typeName = "Cash"
                    )
                }
            }
            item { TransactionList() }
        }
    }
}

@Composable
fun CurrentBalance(
    modifier: Modifier = Modifier,
    cb: Double
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            Text(
                text = "Current Balance",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = cb.toString(),
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.inverseOnSurface
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
        modifier = modifier
            .padding(5.dp)
    ) {
        Column {
            Icon(imageVector = icon , contentDescription = "")
            Text(text = balance.toString())
            Text(text = typeName)
        }
    }
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier
) {
    Row {
        Icon(imageVector = Icons.Filled.ReceiptLong, contentDescription = "")
        Text(text = "Transactions")
        Icon(imageVector = Icons.Filled.NavigateNext, contentDescription = "")
    }
}

@Composable
fun TransactionItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Hello",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Time",
                fontSize = 8.sp,
                color = Color.DarkGray
            )
        }
        Text(
            text = "20",
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}