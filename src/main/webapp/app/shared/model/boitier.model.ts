import { ICamera } from 'app/shared/model/camera.model';
import { IBoitierCapteur } from 'app/shared/model/boitier-capteur.model';
import { IBoitierPatient } from 'app/shared/model/boitier-patient.model';

export interface IBoitier {
  id?: number;
  type?: string | null;
  ref?: string | null;
  nbrBranche?: number | null;
  camera?: ICamera | null;
  boitierCapteurs?: IBoitierCapteur[] | null;
  boitierPatients?: IBoitierPatient[] | null;
}

export const defaultValue: Readonly<IBoitier> = {};
