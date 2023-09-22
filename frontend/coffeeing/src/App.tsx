import React from 'react';
import { Routes, Route } from 'react-router-dom';
import SignupPage from 'pages/SignupPage';
import LoginPage from 'pages/LoginPage';
import { NavBarBody } from 'components/NavBar/NavBarBody';
import { RecMainPage } from 'pages/RecMainPage';
import { RecSurveyPage } from 'pages/RecSurveyPage';
import { RecResultPage } from 'pages/RecResultPage';
import { ListPage } from 'pages/ListPage';
import { SearchPage } from 'pages/SearchPage';
import { DetailPage } from 'pages/DetailPage';
import { OauthPage } from 'pages/OauthPage';
import { AfterSignupPage } from 'pages/AfterSignupPage';
import { MemberPage } from 'pages/MemberPage';
import { FeedPage } from 'pages/FeedPage';

function App() {
  return (
    <div>
      <NavBarBody />

      {/* Routes */}
      <Routes>
        <Route path="/signup" element={<SignupPage />}></Route>
        <Route path="/login" element={<LoginPage />}></Route>
        <Route path="/recommend-main" element={<RecMainPage />}></Route>
        <Route path="/recommend-survey" element={<RecSurveyPage />}></Route>
        <Route path="/recommend-result" element={<RecResultPage />}></Route>
        <Route path="/beans" element={<ListPage />}></Route>
        <Route path="/search" element={<SearchPage />}></Route>
        <Route path="/detail/:beans/:id" element={<DetailPage />}></Route>
        <Route path="/oauth" element={<OauthPage />}></Route>
        <Route
          path="/signup/additonal-info"
          element={<AfterSignupPage />}
        ></Route>
        <Route path="/member/:id" element={<MemberPage />}></Route>
        <Route path="/feeds" element={<FeedPage />}></Route>
      </Routes>
    </div>
  );
}

export default App;
