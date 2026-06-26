export interface Product {
  id: number;
  name: string;
  nomeIt?: string;
  nomeEn?: string;
  imageUrl: string;
  img?: string;
  price: number;
  prezzo?: number;
  listPrice?: number;
  rating: number;
  reviewsCount: number;
  colors?: string[];
}
