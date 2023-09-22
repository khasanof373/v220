import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { VehicleFormService, VehicleFormGroup } from './vehicle-form.service';
import { IVehicle } from '../vehicle.model';
import { VehicleService } from '../service/vehicle.service';
import { IOcppTag } from 'app/entities/ocpp-tag/ocpp-tag.model';
import { OcppTagService } from 'app/entities/ocpp-tag/service/ocpp-tag.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;
  vehicle: IVehicle | null = null;
  statusValues = Object.keys(Status);

  ocppTagsSharedCollection: IOcppTag[] = [];
  usersSharedCollection: IUser[] = [];

  editForm: VehicleFormGroup = this.vehicleFormService.createVehicleFormGroup();

  constructor(
    protected vehicleService: VehicleService,
    protected vehicleFormService: VehicleFormService,
    protected ocppTagService: OcppTagService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOcppTag = (o1: IOcppTag | null, o2: IOcppTag | null): boolean => this.ocppTagService.compareOcppTag(o1, o2);

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.vehicle = vehicle;
      if (vehicle) {
        this.updateForm(vehicle);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.vehicleFormService.getVehicle(this.editForm);
    if (vehicle.id !== null) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(vehicle: IVehicle): void {
    this.vehicle = vehicle;
    this.vehicleFormService.resetForm(this.editForm, vehicle);

    this.ocppTagsSharedCollection = this.ocppTagService.addOcppTagToCollectionIfMissing<IOcppTag>(
      this.ocppTagsSharedCollection,
      vehicle.tag
    );
    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, vehicle.user);
  }

  protected loadRelationshipsOptions(): void {
    this.ocppTagService
      .query()
      .pipe(map((res: HttpResponse<IOcppTag[]>) => res.body ?? []))
      .pipe(map((ocppTags: IOcppTag[]) => this.ocppTagService.addOcppTagToCollectionIfMissing<IOcppTag>(ocppTags, this.vehicle?.tag)))
      .subscribe((ocppTags: IOcppTag[]) => (this.ocppTagsSharedCollection = ocppTags));

    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.vehicle?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
