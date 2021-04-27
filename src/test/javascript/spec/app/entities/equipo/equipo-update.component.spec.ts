import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProquiEquiposVentaTestModule } from '../../../test.module';
import { EquipoUpdateComponent } from 'app/entities/equipo/equipo-update.component';
import { EquipoService } from 'app/entities/equipo/equipo.service';
import { Equipo } from 'app/shared/model/equipo.model';

describe('Component Tests', () => {
  describe('Equipo Management Update Component', () => {
    let comp: EquipoUpdateComponent;
    let fixture: ComponentFixture<EquipoUpdateComponent>;
    let service: EquipoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProquiEquiposVentaTestModule],
        declarations: [EquipoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EquipoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Equipo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Equipo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
