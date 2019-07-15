package br.com.thomas.tmdbtop.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.thomas.tmdbtop.factory.AppViewModelFactory
import br.com.thomas.tmdbtop.presentation.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModels(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}