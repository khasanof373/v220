import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVehicle, NewVehicle } from '../vehicle.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVehicle for edit and NewVehicleFormGroupInput for create.
 */
type VehicleFormGroupInput = IVehicle | PartialWithRequiredKeyOf<NewVehicle>;

type VehicleFormDefaults = Pick<NewVehicle, 'id'>;

type VehicleFormGroupContent = {
  id: FormControl<IVehicle['id'] | NewVehicle['id']>;
  modelId: FormControl<IVehicle['modelId']>;
  manufactureId: FormControl<IVehicle['manufactureId']>;
  imageUrl: FormControl<IVehicle['imageUrl']>;
  status: FormControl<IVehicle['status']>;
  carNumber: FormControl<IVehicle['carNumber']>;
  tag: FormControl<IVehicle['tag']>;
  user: FormControl<IVehicle['user']>;
};

export type VehicleFormGroup = FormGroup<VehicleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VehicleFormService {
  createVehicleFormGroup(vehicle: VehicleFormGroupInput = { id: null }): VehicleFormGroup {
    const vehicleRawValue = {
      ...this.getFormDefaults(),
      ...vehicle,
    };
    return new FormGroup<VehicleFormGroupContent>({
      id: new FormControl(
        { value: vehicleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      modelId: new FormControl(vehicleRawValue.modelId, {
        validators: [Validators.required],
      }),
      manufactureId: new FormControl(vehicleRawValue.manufactureId, {
        validators: [Validators.required],
      }),
      imageUrl: new FormControl(vehicleRawValue.imageUrl),
      status: new FormControl(vehicleRawValue.status, {
        validators: [Validators.required],
      }),
      carNumber: new FormControl(vehicleRawValue.carNumber, {
        validators: [Validators.required],
      }),
      tag: new FormControl(vehicleRawValue.tag),
      user: new FormControl(vehicleRawValue.user),
    });
  }

  getVehicle(form: VehicleFormGroup): IVehicle | NewVehicle {
    return form.getRawValue() as IVehicle | NewVehicle;
  }

  resetForm(form: VehicleFormGroup, vehicle: VehicleFormGroupInput): void {
    const vehicleRawValue = { ...this.getFormDefaults(), ...vehicle };
    form.reset(
      {
        ...vehicleRawValue,
        id: { value: vehicleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): VehicleFormDefaults {
    return {
      id: null,
    };
  }
}
