import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VehicleService } from '../service/vehicle.service';

import { VehicleComponent } from './vehicle.component';

describe('Vehicle Management Component', () => {
  let comp: VehicleComponent;
  let fixture: ComponentFixture<VehicleComponent>;
  let service: VehicleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'vehicle', component: VehicleComponent }]), HttpClientTestingModule],
      declarations: [VehicleComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(VehicleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VehicleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(VehicleService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.vehicles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to vehicleService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getVehicleIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getVehicleIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
