/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { DataCenterTestModule } from '../../../test.module';
import { DcAuthorityDetailComponent } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority-detail.component';
import { DcAuthorityService } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.service';
import { DcAuthority } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.model';

describe('Component Tests', () => {

    describe('DcAuthority Management Detail Component', () => {
        let comp: DcAuthorityDetailComponent;
        let fixture: ComponentFixture<DcAuthorityDetailComponent>;
        let service: DcAuthorityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcAuthorityDetailComponent],
                providers: [
                    DcAuthorityService
                ]
            })
            .overrideTemplate(DcAuthorityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcAuthorityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcAuthorityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DcAuthority(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dcAuthority).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
