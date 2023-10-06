import { IBoitierCapteur } from 'app/shared/model/boitier-capteur.model';

export interface ICapteur {
  id?: number;
  type?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  valeurMin?: number | null;
  valeurMax?: number | null;
  boitierCapteurs?: IBoitierCapteur[] | null;
}

export const defaultValue: Readonly<ICapteur> = {};
