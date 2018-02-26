/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DataCenterTestModule } from '../../../test.module';
import { DcDepartmentComponent } from '../../../../../../main/webapp/app/entities/dc-department/dc-department.component';
import { DcDepartmentService } from '../../../../../../main/webapp/app/entities/dc-department/dc-department.service';
import { DcDepartment } from '../../../../../../main/webapp/app/entities/dc-department/dc-department.model';

describe('Component Tests', () => {

    describe('DcDepartment Management Component', () => {
        let comp: DcDepartmentComponent;
        let fixture: ComponentFixture<DcDepartmentComponent>;
        let service: DcDepartmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcDepartmentComponent],
                providers: [
                    DcDepartmentService
                ]
            })
            .overrideTemplate(DcDepartmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcDepartmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcDepartmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DcDepartment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dcDepartments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
