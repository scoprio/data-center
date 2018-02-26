/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { DataCenterTestModule } from '../../../test.module';
import { DcDepartmentDetailComponent } from '../../../../../../main/webapp/app/entities/dc-department/dc-department-detail.component';
import { DcDepartmentService } from '../../../../../../main/webapp/app/entities/dc-department/dc-department.service';
import { DcDepartment } from '../../../../../../main/webapp/app/entities/dc-department/dc-department.model';

describe('Component Tests', () => {

    describe('DcDepartment Management Detail Component', () => {
        let comp: DcDepartmentDetailComponent;
        let fixture: ComponentFixture<DcDepartmentDetailComponent>;
        let service: DcDepartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcDepartmentDetailComponent],
                providers: [
                    DcDepartmentService
                ]
            })
            .overrideTemplate(DcDepartmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcDepartmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcDepartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DcDepartment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dcDepartment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
