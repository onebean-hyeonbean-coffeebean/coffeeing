import React, { ChangeEvent, useEffect, useState } from "react";
import {SearchBar} from "components/Search/SearchBar";
import { ListBox } from "components/Form/ListBoxForm";
import roast from '../assets/search/free-icon-coffee-beans-3219300.png'
import acidity from '../assets/search/free-icon-lemon-7761399.png'
import body from '../assets/search/free-icon-coffee-cup-4952673.png'
import flavorNote from '../assets/search/free-icon-tongue-599326.png'
import { SelectedFilterTag } from "components/Search/SelectedTag"; 
import { ROAST_ITEMS, ACIDITY_ITEMS, BODY_ITEMS, FLAVOR_NOTE_ITEMS } from "util/constants";
import searchIcon from '../assets/search/search.png'

export const SearchPage = () =>{
  const [selectedRoast, setSelectedRoast] = useState([{label:'미선택', name:'미선택'}])
  const [selectedAcid, setSelectedAcid] = useState([{label:'미선택', name:'미선택'}])
  const [selectedBody, setSelectedBody] = useState([{label:'미선택', name:'미선택'}])
  const [selectedFlavorNote, setSelectedFlavorNote] = useState([{label:'미선택', name:'미선택'}])
  const [title, setTitle] = useState('')
  const handleTextChange = (e:ChangeEvent<HTMLInputElement>) =>{
    setTitle(e.target.value)
  }

  useEffect(()=>{
    const sendRoast = selectedRoast.slice(1,).map(item => item.label).join(', ');
    const sendAcidity = selectedAcid.slice(1,).map(item => item.label).join(', ');
    const sendBody = selectedBody.slice(1,).map(item => item.label).join(', ');
    const sendFlavorNote = selectedFlavorNote.slice(1,).map(item=>item.label).join(',');
  },[selectedRoast,selectedAcid,selectedBody,selectedFlavorNote])

  return(
    <div className="mt-20 flex flex-col items-center">
      <div className="flex flex-col gap-2">
        {/* 검색바 */}
        <div className="flex flex-col justify-center">
          <div className="flex items-center border border-light-roasting w-790px h-16 rounded-md">
          <span className="flex items-center pl-2">
            <img className="w-5 h-5" src={searchIcon} alt="Search Icon" />
          </span>
          <input 
            className="block py-4 pl-2 w-full focus:outline-none"
            type="text" 
            placeholder="검색어를 입력하세요."
            value={title}
            onChange={(e)=>handleTextChange(e)}
          />
        </div>
        </div>
        {/* 필터 listbox */}
        <div className="flex flex-row gap-2 items-start">
          <ListBox 
            label="로스팅" 
            selectedItem={selectedRoast} 
            setSelectedItem={setSelectedRoast} 
            itemList={ROAST_ITEMS}
            src={roast}
          />
          <ListBox 
            label="산미" 
            selectedItem={selectedAcid} 
            setSelectedItem={setSelectedAcid} 
            itemList={ACIDITY_ITEMS}
            src={acidity}
          />
          <ListBox 
            label="바디감" 
            selectedItem={selectedBody} 
            setSelectedItem={setSelectedBody} 
            itemList={BODY_ITEMS}
            src={body}
          />
          <ListBox 
            label="테이스팅 노트" 
            selectedItem={selectedFlavorNote} 
            setSelectedItem={setSelectedFlavorNote} 
            itemList={FLAVOR_NOTE_ITEMS}
            src={flavorNote}
          />
        </div>
        <hr className=" border-half-light" />
        {/* 선택된 태그들 */}
        <div className="flex flex-wrap w-790px gap-1">
          <SelectedFilterTag src={roast} selectedItem={selectedRoast}/>
          <SelectedFilterTag src={acidity} selectedItem={selectedAcid}/>
          <SelectedFilterTag src={body} selectedItem={selectedBody}/>
          <SelectedFilterTag src={flavorNote} selectedItem={selectedFlavorNote}/>
        </div>
      </div>
    </div>
  )
}