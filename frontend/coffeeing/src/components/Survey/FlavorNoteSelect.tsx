import React, {Dispatch, SetStateAction,useEffect,useState} from "react"
import { useSelector, useDispatch } from "react-redux"
import chocolate from '../../assets/survey/flavor/chocolate.png'
import floral from '../../assets/survey/flavor/floral.png'
import fruity from '../../assets/survey/flavor/grapes.png'
import nutty from '../../assets/survey/flavor/nutty.png'
import spicy from '../../assets/survey/flavor/spicy.png'
import sweet from '../../assets/survey/flavor/sweet.png'
import { AppDispatch, RootState } from 'store/store';
import { addCurrentPage, saveFlavorNote } from "store/surveySlice"
import { useNavigate } from 'react-router-dom';
import { NextButton, BackButton } from "./SurveyButton"
import { Toast } from "components/Toast"

export const FlavorNoteSelect = () => {
  const navigate = useNavigate();
  const survey = useSelector((state:RootState)=>state.survey)
  const dispatch = useDispatch<AppDispatch>();
  const [isFinalPage, setIsFinalPage] = useState(true);
  const [selectedChocolate, setSelectedChocolate] = useState(false);
  const [selectedFloral, setSelectedFloral] = useState(false);
  const [selectedFruity, setSelectedFruity] = useState(false);
  const [selectedNutty, setSelectedNutty] = useState(false);
  const [selectedSpicy, setSelectedSpicy] = useState(false);
  const [selectedSweety, setSelectedSweety] = useState(false);
  const [myFlavor, setMyFlavor] = useState(Array<string>);
  useEffect(()=>{
    setIsFinalPage(survey.currentPage===survey.totalPage)
  },[])
  const data = [
    {src:chocolate, label:'초콜릿', isSelected:selectedChocolate, setIsSelected:setSelectedChocolate, keyword:'chocolate'},
    {src:floral, label:'플로럴', isSelected:selectedFloral, setIsSelected:setSelectedFloral, keyword:'floral'},
    {src:fruity, label:'과일', isSelected:selectedFruity, setIsSelected:setSelectedFruity, keyword:'fruity'},
    {src:nutty, label:'견과류', isSelected:selectedNutty, setIsSelected:setSelectedNutty, keyword:'nutty'},
    {src:spicy, label:'매콤', isSelected:selectedSpicy, setIsSelected:setSelectedSpicy, keyword:'spicy'},
    {src:sweet, label:'달콤', isSelected:selectedSweety, setIsSelected:setSelectedSweety, keyword:'sweety'},
  ]

  const handleFlavorSelect = (isSelected: boolean, setIsSelected: Dispatch<SetStateAction<boolean>>, keyword: string) => {
    setMyFlavor((prevMyFlavor) => {
      if (prevMyFlavor.includes(keyword)) {
        // Remove keyword from the array
        return prevMyFlavor.filter((item) => item !== keyword);
      } else {
        // Add keyword to the array
        return [...prevMyFlavor, keyword];
      }
    });
    setIsSelected(!isSelected); // Toggle the isSelected state
  }

  // 캡슐 일 때 -> 머신으로 이동시키기
  const handleFlavorSubmit = ()=>{
    if (myFlavor.length>0) {
      const flavor = myFlavor.toString()
      dispatch(addCurrentPage())
      dispatch(saveFlavorNote(flavor))
    } else {
      Toast.fire('선호하는 맛이나 향을 선택해주세요','','warning')
    }
  }
  // 원두 일 때 -> 결과 받기
  const handleSurveySubmit = ()=>{
    if (myFlavor.length>0) {
      const flavor = myFlavor.toString()
      // console.log(flavor)
      dispatch(saveFlavorNote(flavor))
      navigate('/recommend-result', {replace:true})
    } else {
      Toast.fire('선호하는 맛이나 향을 선택해주세요','','warning')
    }
  }
  return(
    <div className='flex flex-col items-center gap-10 mt-10'>
      {/* 설문 상단 */}
      <div className='flex flex-col items-center gap-2'>
        <p>{survey.currentPage}/{survey.totalPage}</p>
        <p className='text-2xl font-bold'>선호하는 맛이나 향들을 선택해주세요</p>
        <p>(최대 6개 선택 가능)</p>
        <p className='flex w-560px h-2.5 rounded-lg bg-process-bar'>
          <span
            className={`botton-0 left-0 ${survey.totalPage === 4 ? (myFlavor.length>0 ? 'w-full transition-width duration-500 ease-in-out' : 'w-3/4') : (myFlavor.length>0  ? 'w-4/5 transition-width duration-500 ease-in-out' : 'w-3/5')} h-2.5 rounded-lg bg-half-light`}
          ></span>
        </p>
      </div>
      {/* 설문 사진 */}
      <div className='grid grid-rows-6 sm:grid-rows-3 lg:grid-rows-2 grid-flow-col gap-10 '>
        {
          data.map((item) => {
            const { src, label, isSelected, setIsSelected, keyword } = item;
            return (
              <div className={`w-64 h-60 flex flex-col items-center ${isSelected ? 'bg-select-img' : ''} rounded-xl`} key={src}>
                <img
                  className={`w-52 h-52 origin-center transform hover:scale-105 hover:translate-y-[-10px] cursor-pointer`}
                  src={src}
                  onClick={()=>handleFlavorSelect(isSelected, setIsSelected, keyword)}
                />
                <p>{label}</p>
              </div>
            );
          })
        }
      </div>
      {/* 버튼 */}
      {!isFinalPage 
      ?
      <div className='flex gap-10 mb-10'>
        <BackButton/>
        <NextButton handleClick={handleFlavorSubmit} label="다음"/>
      </div>
      :
      <div className="flex gap-10 mb-20">
        <BackButton/>
        <NextButton handleClick={handleSurveySubmit} label="제출하기"/>
      </div>
      }
    </div>
  )
}