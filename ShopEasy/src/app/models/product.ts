export interface Product {
  id: number;
  name: string;
  imageUrl: string;
  price: number;
  listPrice?: number;
  rating: number;
  reviewsCount: number;
  colors?: string[];
}
