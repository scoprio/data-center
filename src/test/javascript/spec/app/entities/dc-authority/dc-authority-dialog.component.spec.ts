/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { DataCenterTestModule } from '../../../test.module';
import { DcAuthorityDialogComponent } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority-dialog.component';
import { DcAuthorityService } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.service';
import { DcAuthority } from '../../../../../../main/webapp/app/entities/dc-authority/dc-authority.model';
import { DcDepartmentService } from '../../../../../../main/webapp/app/entities/dc-department';
import { DcRegionService } from '../../../../../../main/webapp/app/entities/dc-region';

describe('Component Tests', () => {

    describe('DcAuthority Management Dialog Component', () => {
        let comp: DcAuthorityDialogComponent;
        let fixture: ComponentFixture<DcAuthorityDialogComponent>;
        let service: DcAuthorityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [DataCenterTestModule],
                declarations: [DcAuthorityDialogComponent],
                providers: [
                    DcDepartmentService,
                    DcRegionService,
                    DcAuthorityService
                ]
            })
            .overrideTemplate(DcAuthorityDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DcAuthorityDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DcAuthorityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DcAuthority(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dcAuthority = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dcAuthorityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DcAuthority();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dcAuthority = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dcAuthorityListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
