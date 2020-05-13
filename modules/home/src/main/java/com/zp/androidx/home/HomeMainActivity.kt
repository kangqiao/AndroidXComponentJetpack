package com.zp.androidx.home

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.compose.*
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.geometry.Offset
import androidx.ui.geometry.RRect
import androidx.ui.geometry.Radius
import androidx.ui.geometry.Rect
import androidx.ui.graphics.*
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextDecoration
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zp.androidx.base.arch.BaseActivity
import com.zp.androidx.base.config.RouterConfig
import com.zp.androidx.base.config.RouterExtras
import com.zp.androidx.ext.view.HintEditText
import com.zp.androidx.ext.view.HintEditText2
import com.zp.androidx.ext.view.fontSize10
import org.w3c.dom.Text
import timber.log.Timber
import androidx.ui.layout.wrapContentWidth as wrapContentWidth1

/**
 * Created by zhaopan on 2020/5/10
 */

@Deprecated("测试, 做模块独立运行入口.")
@Route(path = RouterConfig.Home.MAIN, name = "Home模块首页", extras = RouterExtras.FLAG_LOGIN)
class HomeMainActivity : BaseActivity() {
    companion object {
        fun open() {
            ARouter.getInstance().build(RouterConfig.Home.MAIN).navigation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSwipeBackEnable(false)
        Timber.d("进入Home模块")

        setContent {
            MaterialTheme {
                SwitchDemo()
            }
        }
    }
}

@Composable
fun homeView() {
    ConstraintLayout(constraintSet = ConstraintSet {
        val half = createGuidelineFromTop(percent = 0.3f)
        val tvMain = tag("tvMain").apply {
            top constrainTo half
        }
        val btnLoadData = tag("btnLoadData").apply {
            top constrainTo tvMain.bottom
        }
    }, modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxSize()) {
        Text("Test模块首页Main", modifier = Modifier.tag("tvMain"))

        Button(onClick = {
            Timber.d("加载数据中....")
        }, modifier = Modifier.tag("btnLoadData")) {
            Text("加载数据")
        }
    }
}

@Composable
fun SwitchDemo() {
    val checkedState = state { true }
    Switch(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it }
    )
}

@Composable
fun ScaffoldDemo() {
    val materialBlue700= Color(0xFF1976D2)
    Scaffold(
        scaffoldState = ScaffoldState(DrawerState.Opened),
        topAppBar = { TopAppBar(title = {Text("TopAppBar")},backgroundColor = materialBlue700)  },
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        floatingActionButton = { FloatingActionButton(onClick = {}){
            Text("X")
        } },
        drawerContent = { Text(text = "drawerContent") },
        bodyContent = { Text("BodyContent") },
        bottomAppBar = { BottomAppBar(backgroundColor = materialBlue700) { Text("BottomAppBar") } }
    )
}

@Composable
fun ModalDrawerLayoutSample() {
    val (state, onStateChange) = state { DrawerState.Closed }

    ModalDrawerLayout(
        drawerState = state,
        onStateChange = onStateChange,
        drawerContent = {
            Column {
                Text("Text in Drawer")
                Button(onClick = { onStateChange(DrawerState.Closed) }) {
                    Text("Close Drawer")
                }
            }
        },
        bodyContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Text in Bodycontext")
                Button(onClick = { onStateChange(DrawerState.Opened) }) {
                    Text("Click to open")
                }
            }
        }
    )
}

@Composable
fun CheckBoxDemo() {
    val checkedState = state { true }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it }
    )
}

@Composable
fun AlertDialogSample() {
    MaterialTheme {
        Column {
            val openDialog = state { true }

            Button( onClick = {
                openDialog.value = true
            }){
                Text("Click me")
            }

            if (openDialog.value) {
                AlertDialog(
                    onCloseRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text("Dialog Title")
                    },
                    text = {
                        Text("Here is a text ")
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }){
                            Text("This is the Confirm Button")
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                openDialog.value = false
                            }){
                            Text("This is the dismiss Button")
                        }
                    },
                    buttonLayout = AlertDialogButtonLayout.Stacked
                )
            }
        }

    }
}

@Composable
fun StackExample() {
    Stack(modifier = Modifier.fillMaxWidth()) {
        Text("This text is drawed first",modifier = Modifier.gravity(Alignment.TopCenter))
        Box(modifier = Modifier.padding(16.dp).gravity(Alignment.TopCenter).fillMaxHeight().preferredWidth(50.dp), backgroundColor = Color.Blue)
        Text("This text is drawed last",modifier = Modifier.gravity(Alignment.Center))
        FloatingActionButton(
            contentColor =  Color.Blue,modifier = Modifier.gravity(Alignment.BottomEnd) + Modifier.padding(12.dp),onClick = {}
        ){
            Text("x")
        }
    }
}

@Composable
fun HandleTextFieldChanges2() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val state = state { TextFieldValue("") }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(textDecoration = TextDecoration.Underline),
            value = state.value,
            onValueChange = { state.value = it }
        )
        Text("The textfield has this text: "+state.value.text)
    }
}

@Composable
fun VerticalScrollerExample() {
    VerticalScroller {
        //Only one child is allowed in a VerticalScroller
        Column {
            for (i in 0..100) {
                Text("$i Hello World!", style =(MaterialTheme.typography).body1)
            }
        }
    }
}

@Composable
fun HorizontalScrollerExample() {
    HorizontalScroller {
        //Only one child is allowed in a HorizontalScroller
        Row {
            for (i in 0..100) {
                Text("$i Hello World!", style =(MaterialTheme.typography).body1)
            }
        }
    }
}

@Composable
fun ClickableSample() {

    val count = state { 0 }
    Clickable(onClick = { count.value += 1 }) {
        // content that you want to make clickable
        Text("You have clicked this text: "+count.value.toString())
    }
}

@Composable
fun CanvasDrawExample() {
    Canvas(modifier = Modifier.preferredHeight(60.dp)) {
        val paint = Paint()
        val centerY = 0f

        // draw rect
        paint.color = Color.Blue
        drawRect(
            Rect(
                0f,
                centerY + 10,
                size.width.value,
                55f
            ),
            paint
        )

        // draw circle
        drawCircle(
            Offset(50f, 200f), 40f, paint
        )

        //Draw a line
        paint.color = Color.Red
        paint.strokeWidth = 5f
        drawLine(
            Offset(20f, 0f),
            Offset(200f, 200f),
            paint
        )

    }
}

@Composable
fun BoxDemo(){
    Column(modifier = Modifier.fillMaxWidth() + Modifier.wrapContentSize(Alignment.Center)) {
        Box(
            modifier = Modifier.preferredSize(100.dp),
            backgroundColor = Color.Red,
            shape = object : Shape {
                override fun createOutline(size: PxSize, density: Density): Outline {
                    return Outline.Rounded(RRect(size.toRect(), topLeft = Radius.circular(50f)) )
                }
            }
        ) {}
    }
}

@Composable
fun AdapterListDemo() {
    AdapterList(
        data = listOf(
            "A", "B", "C", "D"
        )+((0..100).map { it.toString() }),
        modifier = Modifier.None,
        itemCallback = {item->

            Log.d("COMPOSE", "This get rendered $item")
            when (item) {
                "A" -> {
                    Text(text = item, style = TextStyle(fontSize = 80.sp))
                }
                "B" -> {
                    Button(onClick = {}) {
                        Text(text = item, style = TextStyle(fontSize = 80.sp))
                    }
                }
                "C" -> {
                    //Do Nothing
                }
                "D" -> {
                    Text(text = item)
                }
                else -> {
                    Text(text = item, style = TextStyle(fontSize = 80.sp))
                }
            }
        }
    )
}


@Composable
fun Example() {

    val countState = state{0}
    Column {
        Button(backgroundColor = MaterialTheme.colors.secondary, onClick = {countState.value++}) {
            Text("count up")
        }
        Text("You have clicked the Button "+countState.value.toString() +" times")
    }

}

@Composable
fun recomposeDemo() {

    var countState = 0

    Recompose { recompose ->
        Column {
            Text("CountState is: " + countState)

            Button(onClick = { countState++ }) {
                Text("Count up")
            }

            Button(onClick = {
                recompose()
            }) {
                Text("I want to recompose")
            }
        }
    }
}

@Composable
fun testHitTextField() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Hello HintEditText")
        HintEditText2(
            hintText = "hint content zhaopan",
            modifier = Modifier.fillMaxWidth()
        )
    }

}


@Composable
fun HandleTextFieldChanges() {
    Column {
        val state = state { TextFieldValue("Hello World zhaopan123") }

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = state.value,
            onValueChange = { state.value = it }
        )
        Text("The textfield has this text: " + state.value)
    }
}

@Composable
fun HomePage() {
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        val tvGoHome = Button(onClick = {
            Timber.d("go homeUI")

        }) {
            Text(text = "进入Home模块首页")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        testHitTextField()
    }
}