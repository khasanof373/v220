import { Status } from 'app/entities/enumerations/status.model';

import { IVehicle, NewVehicle } from './vehicle.model';

export const sampleWithRequiredData: IVehicle = {
  id: 47420,
  modelId: 92447,
  manufactureId: 63692,
  status: Status['UNKNOWN'],
  carNumber: 'Портмоне желто-коричневый Advanced',
};

export const sampleWithPartialData: IVehicle = {
  id: 79375,
  modelId: 40163,
  manufactureId: 69747,
  imageUrl: 'резервное',
  status: Status['APPROVED'],
  carNumber: 'Майка копирование',
};

export const sampleWithFullData: IVehicle = {
  id: 56803,
  modelId: 15725,
  manufactureId: 81300,
  imageUrl: 'Свободный',
  status: Status['APPROVED'],
  carNumber: 'пл.',
};

export const sampleWithNewData: NewVehicle = {
  modelId: 1185,
  manufactureId: 11470,
  status: Status['UNKNOWN'],
  carNumber: 'support',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
