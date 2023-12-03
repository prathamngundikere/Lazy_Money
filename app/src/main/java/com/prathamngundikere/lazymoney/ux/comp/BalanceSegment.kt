package com.prathamngundikere.lazymoney.ux.comp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prathamngundikere.lazymoney.R

@Composable
fun BalanceSegment() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.current_balance),
                    modifier = Modifier,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "₹25678",
                    modifier = Modifier,
                    fontSize = 46.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BalanceBox(
                        icon = Icons.Default.AccountBalance,
                        description = stringResource(R.string.bank_balance),
                        amount = 2500.00f
                    )
                    BalanceBox(
                        icon = Icons.Default.Payments,
                        description = stringResource(R.string.cash),
                        amount = 678.00f
                    )
                }
            }
        }
    }
}

@Composable
fun BalanceBox(
    icon: ImageVector,
    description: String,
    amount: Float
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .border(2.dp, color = Color.Black, shape = RectangleShape),
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = description
            )
            Text(
                text = "₹$amount",
                modifier = Modifier,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                modifier = Modifier,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
@Preview
@Composable
fun hello(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            WelcomeSegment()
            BalanceSegment()
        }
    }
}