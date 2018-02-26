/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { DataCenterTestModule } from '../../../test.module';
import { DcMenuDetailComponent } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu-detail.component';
import { DcMenuService } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu.service';
import { DcMenu } from '../../../../../../main/webapp/app/entities/dc-menu/dc-menu.model';

describe('Component Tests', () => {

    describe('DcMenu Management Detail Component', () => {
        let comp: DcMenuDetailComponent;
        let fixture: ComponentFixture<DcMenuDetailComponent>;
        let service: DcMenuService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcMenuDetailComponent],
                providers: [
                    DcMenuService
                ]
            })
            .overrideTemplate(DcMenuDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcMenuDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcMenuService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DcMenu(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dcMenu).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
