import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ProquiEquiposVentaTestModule } from '../../../test.module';
import { EquipoDetailComponent } from 'app/entities/equipo/equipo-detail.component';
import { Equipo } from 'app/shared/model/equipo.model';

describe('Component Tests', () => {
  describe('Equipo Management Detail Component', () => {
    let comp: EquipoDetailComponent;
    let fixture: ComponentFixture<EquipoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ equipo: new Equipo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProquiEquiposVentaTestModule],
        declarations: [EquipoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EquipoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load equipo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equipo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
