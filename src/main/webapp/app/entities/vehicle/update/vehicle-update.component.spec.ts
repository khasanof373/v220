import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VehicleFormService } from './vehicle-form.service';
import { VehicleService } from '../service/vehicle.service';
import { IVehicle } from '../vehicle.model';
import { IOcppTag } from 'app/entities/ocpp-tag/ocpp-tag.model';
import { OcppTagService } from 'app/entities/ocpp-tag/service/ocpp-tag.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { VehicleUpdateComponent } from './vehicle-update.component';

describe('Vehicle Management Update Component', () => {
  let comp: VehicleUpdateComponent;
  let fixture: ComponentFixture<VehicleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let vehicleFormService: VehicleFormService;
  let vehicleService: VehicleService;
  let ocppTagService: OcppTagService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VehicleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(VehicleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    vehicleFormService = TestBed.inject(VehicleFormService);
    vehicleService = TestBed.inject(VehicleService);
    ocppTagService = TestBed.inject(OcppTagService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OcppTag query and add missing value', () => {
      const vehicle: IVehicle = { id: 456 };
      const tag: IOcppTag = { id: 74670 };
      vehicle.tag = tag;

      const ocppTagCollection: IOcppTag[] = [{ id: 21307 }];
      jest.spyOn(ocppTagService, 'query').mockReturnValue(of(new HttpResponse({ body: ocppTagCollection })));
      const additionalOcppTags = [tag];
      const expectedCollection: IOcppTag[] = [...additionalOcppTags, ...ocppTagCollection];
      jest.spyOn(ocppTagService, 'addOcppTagToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vehicle });
      comp.ngOnInit();

      expect(ocppTagService.query).toHaveBeenCalled();
      expect(ocppTagService.addOcppTagToCollectionIfMissing).toHaveBeenCalledWith(
        ocppTagCollection,
        ...additionalOcppTags.map(expect.objectContaining)
      );
      expect(comp.ocppTagsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const vehicle: IVehicle = { id: 456 };
      const user: IUser = { id: 89457 };
      vehicle.user = user;

      const userCollection: IUser[] = [{ id: 88955 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ vehicle });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const vehicle: IVehicle = { id: 456 };
      const tag: IOcppTag = { id: 8659 };
      vehicle.tag = tag;
      const user: IUser = { id: 3063 };
      vehicle.user = user;

      activatedRoute.data = of({ vehicle });
      comp.ngOnInit();

      expect(comp.ocppTagsSharedCollection).toContain(tag);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.vehicle).toEqual(vehicle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicle>>();
      const vehicle = { id: 123 };
      jest.spyOn(vehicleFormService, 'getVehicle').mockReturnValue(vehicle);
      jest.spyOn(vehicleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicle }));
      saveSubject.complete();

      // THEN
      expect(vehicleFormService.getVehicle).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(vehicleService.update).toHaveBeenCalledWith(expect.objectContaining(vehicle));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicle>>();
      const vehicle = { id: 123 };
      jest.spyOn(vehicleFormService, 'getVehicle').mockReturnValue({ id: null });
      jest.spyOn(vehicleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicle: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: vehicle }));
      saveSubject.complete();

      // THEN
      expect(vehicleFormService.getVehicle).toHaveBeenCalled();
      expect(vehicleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IVehicle>>();
      const vehicle = { id: 123 };
      jest.spyOn(vehicleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ vehicle });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(vehicleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOcppTag', () => {
      it('Should forward to ocppTagService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ocppTagService, 'compareOcppTag');
        comp.compareOcppTag(entity, entity2);
        expect(ocppTagService.compareOcppTag).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
