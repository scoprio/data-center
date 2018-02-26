/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DataCenterTestModule } from '../../../test.module';
import { DcMenuComponent } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu.component';
import { DcMenuService } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu.service';
import { DcMenu } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu.model';

describe('Component Tests', () => {

    describe('DcMenu Management Component', () => {
        let comp: DcMenuComponent;
        let fixture: ComponentFixture<DcMenuComponent>;
        let service: DcMenuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcMenuComponent],
                providers: [
                    DcMenuService
                ]
            })
            .overrideTemplate(DcMenuComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcMenuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcMenuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DcMenu(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dcMenus[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
