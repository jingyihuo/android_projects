package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {
    // backing property
    // val 是因为MutableLiveData对象不变，只是里面存储的数据会更改
    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord : LiveData<String>
        get() = _currentScrambledWord

    // 后备属性 backing property
    // 只能在ViewModel内部修改
    private val _currentWordCount = MutableLiveData<Int>(0)
    // 外部只读
    val currentWordCount : LiveData<Int>
        get() = _currentWordCount

    private val _score = MutableLiveData<Int>(0)
    val score : LiveData<Int>
        get() = _score

    private val wordsList : MutableList<String> = mutableListOf()
    private lateinit var currentWord : String



    public fun getNextWord() {
        /*
        * 从 allWordsList 中获取一个随机单词并将其赋值给 currentWord.。
        * 打乱 currentWord 中的字母以创建一个乱序词并将其赋值给 currentScrambledWord。
        * 处理乱序词与理顺词（理清字母顺序的单词）相同的情况。
        * 确保不会在游戏期间重复显示同一个单词。
        * */
        currentWord = allWordsList.random()

        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord)) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }

    }

    init {
        Log.d("GameFragment", "GameViewMode created")
        getNextWord()
    }

    fun nextWord() : Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String) : Boolean {
        return if (playerWord.equals(currentWord)) {
            increaseScore()
            Log.i("test", "${ score }")
            true
        } else false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}