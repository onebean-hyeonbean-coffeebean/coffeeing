import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import recBgImg from '../assets/surveyMainImg.png'
import {AppDispatch} from 'store/store'
import { useDispatch } from 'react-redux';
import { resetSurvey } from 'store/surveySlice';

export const RecMainPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  // survey 선택 초기화
  useEffect(()=>{
    dispatch(resetSurvey())
    // console.log('reset하기')

  },[])

  const handleStartSurvey = ()=>{
    navigate('/recommend-survey')
  }


  return (
    <div className='relative flex items-center justify-center w-full'>
      <img className='w-full h-[calc(100vh-64px)] brightness-50' src={recBgImg} alt="배경 이미지" />
      <div className='absolute flex flex-col items-center gap-10 top-1/3'>
        <div className='flex flex-col items-center'>
          <p className='text-4xl text-white z-10'>본인의 취향에 맞는</p>
          <p className='text-4xl text-white z-10'>원두와 캡슐을 추천 받아보세요</p>
        </div>
        <button 
          className='bg-white text-black w-60 h-12 '
          onClick={handleStartSurvey}
          >시작하기</button>
      </div>
    </div>
  )
}