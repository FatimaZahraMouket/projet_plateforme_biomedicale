import { IVideo } from 'app/shared/model/video.model';
import { IBoitier } from 'app/shared/model/boitier.model';

export interface ICamera {
  id?: number;
  nom?: string | null;
  resolution?: string | null;
  videos?: IVideo[] | null;
  boitiers?: IBoitier[] | null;
}

export const defaultValue: Readonly<ICamera> = {};
