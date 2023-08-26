package com.example.shopit

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.shopit.data.DefaultDatabaseRepository
import com.example.shopit.data.model.Product
import com.example.shopit.data.remote.RemoteDatabaseRepository
import com.example.shopit.ui.screens.CartScreenViewModel
import com.example.shopit.ui.screens.HomeScreenViewModel
import com.example.shopit.ui.screens.SuccessScreen
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
const val idOne = "img1"
const val idTwo = "img2"
const val urlOne = "img.1"
const val urlTwo = "img.2"
val productList = listOf(
    Product(
        _id = idOne,
        main_image = urlOne,
        primary_category = "Toys"
    ),
    Product(
        _id = idTwo,
        main_image = urlTwo,
        primary_category = "Electronics"
    )
)
val categories = listOf<String>("All", "Toys", "Household")
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var repository: RemoteDatabaseRepository
    lateinit var viewModel: HomeScreenViewModel
    lateinit var cartScreenViewModel: CartScreenViewModel


    @get:Rule
    val rule = createComposeRule()

    @Before
    fun before_test(){
        val database = Firebase.database
        repository = DefaultDatabaseRepository(database)
        viewModel = HomeScreenViewModel(repository)
        cartScreenViewModel = CartScreenViewModel(repository)
    }

    @Test
    fun successScreen_assertCategoriesIsPresent(){
        rule.setContent {
            SuccessScreen(
                products = productList,
                categories = categories,
                viewModel = viewModel,
                cartScreenViewModel = cartScreenViewModel,
                navController = rememberNavController(),
                scope = rememberCoroutineScope()
            )
        }
        rule.onNodeWithText("All").assertHasClickAction()
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.shopit", appContext.packageName)
    }
}