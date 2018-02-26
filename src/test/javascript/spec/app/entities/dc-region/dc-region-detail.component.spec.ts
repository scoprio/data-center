/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { DataCenterTestModule } from '../../../test.module';
import { DcRegionDetailComponent } from '../../../../../../main/webapp/app/entities/dc-region/dc-region-detail.component';
import { DcRegionService } from '../../../../../../main/webapp/app/entities/dc-region/dc-region.service';
import { DcRegion } from '../../../../../../main/webapp/app/entities/dc-region/dc-region.model';

describe('Component Tests', () => {

    describe('DcRegion Management Detail Component', () => {
        let comp: DcRegionDetailComponent;
        let fixture: ComponentFixture<DcRegionDetailComponent>;
        let service: DcRegionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcRegionDetailComponent],
                providers: [
                    DcRegionService
                ]
            })
            .overrideTemplate(DcRegionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcRegionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcRegionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DcRegion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dcRegion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
