import { IOcppTag } from 'app/entities/ocpp-tag/ocpp-tag.model';
import { IUser } from 'app/entities/user/user.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IVehicle {
  id: number;
  modelId?: number | null;
  manufactureId?: number | null;
  imageUrl?: string | null;
  status?: Status | null;
  carNumber?: string | null;
  tag?: Pick<IOcppTag, 'id' | 'idTag'> | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewVehicle = Omit<IVehicle, 'id'> & { id: null };
