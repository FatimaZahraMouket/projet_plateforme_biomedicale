import { IExtraUser } from 'app/shared/model/extra-user.model';
import { IMesure } from 'app/shared/model/mesure.model';
import { IMedecinPatient } from 'app/shared/model/medecin-patient.model';
import { IBoitierPatient } from 'app/shared/model/boitier-patient.model';

export interface IPatient {
  id?: number;
  profession?: string | null;
  extraUserId?: IExtraUser | null;
  mesures?: IMesure[] | null;
  medecinPatients?: IMedecinPatient[] | null;
  boitierPatients?: IBoitierPatient[] | null;
}

export const defaultValue: Readonly<IPatient> = {};
