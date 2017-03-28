package com.labo.kaji.commitnowsample

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import com.labo.kaji.fragmentanimations.CubeAnimation
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.withArguments

class MainActivity : AppCompatActivity(), AnkoLogger {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        pushNewFragmentNow()
        pushNewFragmentNow()

        findViewById(R.id.fab).setOnClickListener {
//            pushNewFragment()
            pushNewFragmentNow()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    fun pushNewFragment() {
        val name = "Fragment${++count}"
        info("Transaction for $name: begin")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout, SampleFragment1().withArguments("name" to name))
                .commit()
        info("Transaction for $name: end")
    }

    fun pushNewFragmentNow() {
        val name = "Fragment${++count}"
        info("Transaction for $name: begin")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.layout, SampleFragment1().withArguments("name" to name))
                .commitNow()
        info("Transaction for $name: end")
    }

    open class LifecycleFragment : Fragment(), AnkoLogger {

        val name: String
            get() = arguments.getString("name")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            info("$name: onCreate")
        }

        override fun onAttach(context: Context?) {
            super.onAttach(context)
            info("$name: onAttach")
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            info("$name: onCreateView")
            return super.onCreateView(inflater, container, savedInstanceState)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            info("$name: onViewCreated")
        }

        override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
            super.onInflate(context, attrs, savedInstanceState)
            info("$name: onInflate")
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            info("$name: onActivityCreated")
        }

        override fun onConfigurationChanged(newConfig: Configuration?) {
            super.onConfigurationChanged(newConfig)
            info("$name: onConfigurationChanged")
        }

        override fun onAttachFragment(childFragment: Fragment?) {
            super.onAttachFragment(childFragment)
            info("$name: onAttachFragment")
        }

        override fun onStart() {
            super.onStart()
            info("$name: onStart")
        }

        override fun onResume() {
            super.onResume()
            info("$name: onResume")
        }

        override fun onPause() {
            super.onPause()
            info("$name: onPause")
        }

        override fun onStop() {
            super.onStop()
            info("$name: onStop")
        }

        override fun onDestroyView() {
            super.onDestroyView()
            info("$name: onDestroyView")
        }

        override fun onDetach() {
            super.onDetach()
            info("$name: onDetach")
        }

        override fun onDestroy() {
            super.onDestroy()
            info("$name: onDestroy")
        }

    }

    class SampleFragment1 : LifecycleFragment() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            info("Transaction for child: begin")
            childFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_inner, ChildFragment().withArguments(
                            "name" to "Child$name",
                            "text" to name
                    ))
                    .commitNow()
            info("Transaction for child: end")
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.fragment_parent, container, false).apply {
                setBackgroundColor(Color.GRAY)
            }
        }

        override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
            return CubeAnimation.create(CubeAnimation.RIGHT, enter, 500)
        }

    }

    class ChildFragment : LifecycleFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return TextView(context).apply {
                setBackgroundColor(Color.rgb(Math.floor(Math.random() * 128).toInt() + 64,
                        Math.floor(Math.random() * 128).toInt() + 64,
                        Math.floor(Math.random() * 128).toInt() + 64))
                text = arguments.getString("text")
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
            }
        }

    }

}
