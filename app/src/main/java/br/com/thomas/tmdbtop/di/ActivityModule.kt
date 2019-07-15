package br.com.thomas.tmdbtop.di

import br.com.thomas.tmdbtop.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

}