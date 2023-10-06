import dayjs from 'dayjs';
import { ICamera } from 'app/shared/model/camera.model';

export interface IVideo {
  id?: number;
  nom?: string | null;
  dateCapture?: string | null;
  duree?: number | null;
  camera?: ICamera | null;
}

export const defaultValue: Readonly<IVideo> = {};
