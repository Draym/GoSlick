package com.andres_k.gameToolsLib.utils.tools;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.*;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MovieDecoder {

    public static class Movie {
        public ArrayList<BufferedImage> frames;
        public int frameCount;
        public double duration;
        public int frameRate;

        Movie(ArrayList<BufferedImage> frames, double duration) {
            this.frames = frames;
            this.frameCount = frames.size();
            this.duration = duration;
            this.frameRate = (int)(duration / this.frameCount);
        }
    }

    public static Movie decode(String fileName) throws IOException, JCodecException {
        return decode(fileName, 1);
    }

    public static Movie decode(String fileName, int cutFrameEvery) throws IOException, JCodecException {
        File file = new File(fileName);

        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        ArrayList<BufferedImage> bufferedImages = new ArrayList<>();
        Picture picture;

        int i = -1;
        while (null != (picture = grab.getNativeFrame())) {
            if (i == -1 || i == cutFrameEvery) {
                bufferedImages.add(AWTUtil.toBufferedImage(picture));
                i = 0;
            }
            ++i;
        }
        Format f = JCodecUtil.detectFormat(file);
        Demuxer d = JCodecUtil.createDemuxer(f, file);
        DemuxerTrack vt = d.getVideoTracks().get(0);
        DemuxerTrackMeta dtm = vt.getMeta();
        return new Movie(bufferedImages, dtm.getTotalDuration() * 1000);
    }
}
