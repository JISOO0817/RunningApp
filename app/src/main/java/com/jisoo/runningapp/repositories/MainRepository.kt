package com.jisoo.runningapp.repositories

import com.jisoo.runningapp.db.Run
import com.jisoo.runningapp.db.RunDAO
import javax.inject.Inject

/**
 * 생성자를 통한 의존성 주입
 * */
class MainRepository @Inject constructor(
    val dao: RunDAO
    )
{
    suspend fun insertRun(run: Run) = dao.insertRun(run)

    suspend fun deleteRun(run: Run) = dao.deleteRun(run)

    /**
     * livedata 가 비동기식이므로 지연함수를 쓸 필요가 없다.
     * */
    fun getAllRunsSortedByDate() = dao.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance() = dao.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis() = dao.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed() = dao.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned() = dao.getAllRunsSortedByCaloriesBurned()

    fun getTotalAvgSpeed() = dao.getTotalAvgSpeed()

    fun getTotalDistance() = dao.getTotalDistance();

    fun getTotalCaloriesBurned() = dao.getTotalCaloiesBurnes();

    fun getTotalTimeInMillis() = dao.getTotalTimeInMillis();
}