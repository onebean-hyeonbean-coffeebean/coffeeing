import React from 'react';
import { BeanRating } from 'components/Detail/BeanRating';

export const DetailPage = () => {
  // 더미 데이터, 나중에 받아온 데이터로 연결할 예정
  const capsule = {
    acidity: 0,
    aroma: 'string',
    averageScore: 0,
    body: 0,
    brand: 'string',
    description: 'string',
    id: 0,
    imageUrl: 'string',
    isBookmarked: true,
    name: 'string',
    roast: 0,
  };

  return <BeanRating rate={0} />;
};
