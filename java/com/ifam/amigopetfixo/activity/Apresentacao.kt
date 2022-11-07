package com.ifam.amigopetfixo.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ifam.amigopetfixo.R
import kotlinx.android.synthetic.main.apresentacao.*
import kotlinx.android.synthetic.main.slide1.view.*
import kotlin.math.abs

class Apresentacao : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(R.layout.apresentacao)
        overridePendingTransition(R.anim.fade_in, R.anim.mover_direita)

        val tips = arrayOf(
                Tip("",R.drawable.logo_png0 ,R.drawable.mockup1),
                Tip("",R.drawable.logo_png0 ,R.drawable.mockup2),
                Tip("Na área home você pode interagir adicionando postagem para todos os usuários visualizarem " +
                         "e ainda poderá acompanhar todos os eventos disponíveis para sua cidade.", R.drawable.ic_jornal_dobrado ,R.drawable.fundo3),
                Tip("Em mapa você encontrar Pets Shops, Veterinários, Casas de acolhimento aos animais de rua" +
                         " e ainda ONGs voltados a comunidade pet de sua região.", R.drawable.ic_location_on_map ,R.drawable.fundo4),
                Tip("Em adoções e doações você encontrará uma variedade de produtos de higiene, alimentação, brinquedos," +
                         " remédios e móveis para seus animais queridos. E se você quiser adotar mais um amiguinho poderá encontrar aqui também.", R.drawable.ic_animais_de_estimacao ,R.drawable.fundo5),
                Tip("Divirta-se na nossa comudade.", R.drawable.logo_png ,R.drawable.fundo1)
        )

        addDots(tips.size, 0)

        view_page.adapter = OnboardingAdapter(tips)

        view_page.setPageTransformer(true){page: View, position: Float ->
            page.alpha = 1 - abs(position)
            page.translationX = -position*page.width
            //Log.i("123","${position}
        }

        view_page.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

            override fun onPageSelected(position: Int) {
                addDots(tips.size, position)
            }

        })

        next.setOnClickListener {
            if (view_page.currentItem == tips.size) {
                val intent = Intent(this, Tela_principal::class.java)
                startActivity(intent)
                finish()
            }
            view_page.setCurrentItem(view_page.currentItem+1, true)
        }

        back.setOnClickListener{
            val intent = Intent(this, Tela_principal::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_out, R.anim.mover_esquerda)
    }

    private fun addDots(size: Int, position : Int) {
        dots.removeAllViews()

        Array(size){
            val textView = TextView(baseContext).apply{
                text = getText(R.string.dotted)
                textSize = 35f
                setTextColor(
                        if(position == it)ContextCompat.getColor(baseContext, android.R.color.white)
                        else ContextCompat.getColor(baseContext, android.R.color.darker_gray))
            }
            dots.addView(textView)
        }

    }

    private inner class OnboardingAdapter(val tips: Array<Tip>) : PagerAdapter(){

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.slide1, container, false)

            with(tips[position]){
                view.text.text = tips[position].title
                view.logo.setImageResource(logo)
                view.background = ContextCompat.getDrawable(this@Apresentacao, tips[position].color)
                //view.background = ContextCompat.getDrawable(this@Apresentacao, background)
            }

            container.addView(view)

            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int = tips.size

    }
}