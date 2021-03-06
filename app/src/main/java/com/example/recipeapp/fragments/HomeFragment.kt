package com.example.recipeapp.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.RecipeAdapter
import com.example.recipeapp.ui.MainActivityViewModel
import com.example.recipeapp.ui.RecipeDetailsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.res.ColorStateList
import android.graphics.Color.parseColor



/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private val recipes = arrayListOf<Recipe>()
    private lateinit var thisContext: Context
    private lateinit var progressBar: ProgressBar
    private val recipeAdapter =
        RecipeAdapter(recipes, onClick = { onMovieClick(it) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        thisContext = activity!!.applicationContext
        initViewModel()
        initViews()
    }

    private fun initViews() {
        ivSearch.setOnClickListener{
            val searchInput = etSearch.text.toString()
            viewModel.  getRecipeListSearch(searchInput)
        }
        progressBar = ProgressBar(thisContext)
        val colorCodeDark = parseColor("#D81B60")
        progressBar.indeterminateTintList = ColorStateList.valueOf(colorCodeDark)
        val layoutParameters = RelativeLayout.LayoutParams(200, 200)
        layoutParameters.addRule(RelativeLayout.CENTER_IN_PARENT)
        frameLayout.addView(progressBar, layoutParameters)
        rvRecipesHome.layoutManager = LinearLayoutManager(thisContext, RecyclerView.VERTICAL, false)
        rvRecipesHome.adapter = recipeAdapter
        viewModel.getRecipeList() // Get a random recipes from spoonacular api
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.recipe.observe(this, Observer {
            recipes.clear()
            recipes.addAll(it)
            recipeAdapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progressBar.visibility = ProgressBar.VISIBLE
            } else {
                progressBar.visibility = ProgressBar.GONE
            }
        })
    }

    private fun onMovieClick(recipe: Recipe) {
        val intent = Intent(thisContext, RecipeDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("Recipe", recipe)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}
