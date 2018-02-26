/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DataCenterTestModule } from '../../../test.module';
import { DcAuthorityComponent } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.component';
import { DcAuthorityService } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.service';
import { DcAuthority } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.model';

describe('Component Tests', () => {

    describe('DcAuthority Management Component', () => {
        let comp: DcAuthorityComponent;
        let fixture: ComponentFixture<DcAuthorityComponent>;
        let service: DcAuthorityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcAuthorityComponent],
                providers: [
                    DcAuthorityService
                ]
            })
            .overrideTemplate(DcAuthorityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcAuthorityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcAuthorityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DcAuthority(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dcAuthorities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
