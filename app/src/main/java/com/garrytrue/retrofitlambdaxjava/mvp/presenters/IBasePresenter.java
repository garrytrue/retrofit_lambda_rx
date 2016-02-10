package com.garrytrue.retrofitlambdaxjava.mvp.presenters;


interface IBasePresenter<T> {
    void init(T view);
}
