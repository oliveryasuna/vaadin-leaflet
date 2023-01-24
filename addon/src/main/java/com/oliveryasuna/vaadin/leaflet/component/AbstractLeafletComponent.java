/*
 * Copyright 2023 Oliver Yasuna
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without
 *      specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.oliveryasuna.vaadin.leaflet.component;

import com.oliveryasuna.vaadin.leaflet.js.LeafletPojo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Base class for Leaflet components.
 *
 * @param <C> The type of component to represent the Leaflet element.
 *
 * @author Oliver Yasuna
 */
// TODO: Use `AbstractComposite` once I update `flow-commons`.
// TODO: Need a mechanism to report commands that timeout.
public abstract class AbstractLeafletComponent<C extends Component, L extends LeafletPojo> extends Composite<C> {

  // Constructors
  //--------------------------------------------------

  protected AbstractLeafletComponent(final CompletableFuture<L> pojoInitializer) {
    super();

    this.ready = new CountDownLatch(1);
    this.executor = Executors.newSingleThreadExecutor();

    // The first task is to wait for the POJO to be initialized.
    this.executor.submit(new WaitForPojoTask());

    pojoInitializer.thenAccept(pojo -> {
      this.pojo = pojo;
      this.ready.countDown();
    });
  }

  // Fields
  //--------------------------------------------------

  private final CountDownLatch ready;

  private final ExecutorService executor;

  private L pojo;

  // Methods
  //--------------------------------------------------

  protected void schedule(final Runnable command) {
    getExecutor().submit(command);
  }

  protected void schedule(final Consumer<L> command) {
    schedule(() -> command.accept(getPojo()));
  }

  public boolean isReady() {
    return (getPojo() != null);
  }

  protected abstract Logger logger();

  // Getters/setters
  //--------------------------------------------------

  protected CountDownLatch getReady() {
    return ready;
  }

  protected ExecutorService getExecutor() {
    return executor;
  }

  public L getPojo() {
    return pojo;
  }

  // Nested
  //--------------------------------------------------

  /**
   * Task that waits for the POJO to be initialized.
   *
   * @author Oliver Yasuna
   */
  protected class WaitForPojoTask implements Runnable {

    // Constructors
    //--------------------------------------------------

    public WaitForPojoTask() {
      super();
    }

    // Methods
    //--------------------------------------------------

    @Override
    public void run() {
      try {
        AbstractLeafletComponent.this.ready.await();
      } catch(final InterruptedException e) {
        logger().error("Failed to wait for the map to be initialized.", e);
      }
    }

  }

}
