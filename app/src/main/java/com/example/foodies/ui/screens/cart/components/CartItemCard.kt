package com.example.foodies.ui.screens.cart.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.ui.screens.catalog.models.Digit
import com.example.foodies.ui.screens.catalog.models.compareTo
import com.example.foodies.utils.clickableWithRipple

@SuppressLint("SuspiciousIndentation")
@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    productName: String,
    priceTotal: Int,
    priceOld: Int? = null,
    count: Int,
    onClickMinus: (count: Int) -> Unit,
    onClickPlus: (count: Int) -> Unit,
) {

    var rememberCount by remember {
        mutableStateOf(count)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp,
            ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(96.dp),
            painter = painterResource(id = R.drawable.foodies),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = productName,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black.copy(0.8f),
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickableWithRipple(
                                onClick = {
                                    if (count > 0) {
                                        rememberCount--
                                        onClickMinus(rememberCount)
                                    }
                                }
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp),
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = null,
                            tint = Color(0xFFF15412)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .animateContentSize()
                            .padding(horizontal = 3.dp)
                            .size(24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        rememberCount.toString()
                            .mapIndexed { index, c -> Digit(c, rememberCount, index) }
                            .forEach { digit ->
                                AnimatedContent(
                                    targetState = digit,
                                    transitionSpec = {
                                        if (targetState > initialState) {
                                            slideInVertically { -it } togetherWith slideOutVertically { it }
                                        } else {
                                            slideInVertically { it } togetherWith slideOutVertically { -it }
                                        }
                                    }, label = ""
                                ) { digit ->
                                    Text(
                                        "${digit.digitChar}",
                                        textAlign = TextAlign.Center,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF5F5F5))
                            .clickableWithRipple(
                                onClick = {
                                    rememberCount++
                                    onClickPlus(rememberCount)
                                }
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp),
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            tint = Color(0xFFF15412)
                        )
                    }
                }
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$priceTotal",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                    )
                    if (priceOld != null)
                    Text(
                        text = "$priceOld",
                        style = MaterialTheme.typography.labelMedium,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color(0f, 0f, 0f, 0.6f)
                    )
                }
            }
        }
    }
}