package com.example.shopit

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    lateinit var repository: RemoteDatabaseRepository
//    lateinit var viewModel: HomeScreenViewModel
//    lateinit var cartScreenViewModel: CartScreenViewModel
//    lateinit var apiServiceRepository: ApiServiceRepository
//
//
//    @get:Rule
//    val rule = createComposeRule()
//
//    @Before
//    fun before_test(){
//        val database = Firebase.database
//        repository = DefaultDatabaseRepository(database)
//        viewModel = HomeScreenViewModel(repository)
//        apiServiceRepository = DefaultApiServiceRepository(apiService = DarajaApiService)
//        cartScreenViewModel = CartScreenViewModel(repository)
//    }
//
//    @Test
//    fun successScreen_assertCategoriesIsPresent(){
//        rule.setContent {
//            SuccessScreen(
//                products = productList,
//                categories = categories,
//                viewModel = viewModel,
//                cartScreenViewModel = cartScreenViewModel,
//                navController = rememberNavController(),
//                scope = rememberCoroutineScope()
//            )
//        }
//        rule.onNodeWithText("All").assertHasClickAction()
//    }
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.shopit", appContext.packageName)
//    }
}