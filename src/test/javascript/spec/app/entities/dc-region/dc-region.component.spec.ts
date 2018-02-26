/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DataCenterTestModule } from '../../../test.module';
import { DcRegionComponent } from '../../../../../../main/webapp/app/entities/dc-region/dc-region.component';
import { DcRegionService } from '../../../../../../main/webapp/app/entities/dc-region/dc-region.service';
import { DcRegion } from '../../../../../../main/webapp/app/entities/dc-region/dc-region.model';

describe('Component Tests', () => {

    describe('DcRegion Management Component', () => {
        let comp: DcRegionComponent;
        let fixture: ComponentFixture<DcRegionComponent>;
        let service: DcRegionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcRegionComponent],
                providers: [
                    DcRegionService
                ]
            })
            .overrideTemplate(DcRegionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcRegionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcRegionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DcRegion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dcRegions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
